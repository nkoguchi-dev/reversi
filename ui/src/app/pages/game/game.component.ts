import {Component, inject, OnDestroy, OnInit, Signal, signal, WritableSignal} from '@angular/core';
import {AsyncPipe} from "@angular/common";
import {Subscription} from "rxjs";
import {DebugComponent} from "./components/debug/debug.component";
import {BoardComponent} from "./components/board/board.component";
import {GameStartService} from "../home/services/game-start.service";
import {Disk} from "../../models/disk.model";
import {HorizontalPosition, Position, VerticalPosition} from "../../models/position.model";
import {GameStatus, PlayerStatus} from "../../models/game-state.module";
import {PutDiskResponse, PutDiskService} from "./services/put-disk.service";
import {GameStateService} from "../../services/game-state.service";
import {PlayerStatusComponent} from "./components/player-status/player-status.component";

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [
    AsyncPipe,
    DebugComponent,
    BoardComponent,
    PlayerStatusComponent
  ],
  templateUrl: './game.component.html',
  styleUrl: './game.component.scss'
})
export class GameComponent implements OnInit, OnDestroy {
  private readonly _gameStartService = inject(GameStartService);
  private readonly _putDiskService = inject(PutDiskService);
  private readonly _gameStateService = inject(GameStateService);
  private readonly _subscription: Subscription = new Subscription();
  private _gameState: GameStatus;
  readonly diskMapSignal: WritableSignal<Map<string, Disk | null>>;
  readonly playerStatusSignal: WritableSignal<PlayerStatus>;

  constructor() {
    this._gameState = this._initializeGameState();
    this.diskMapSignal = signal<Map<string, Disk | null>>(this._gameState.diskMap);
    this.playerStatusSignal = signal<PlayerStatus>(this._gameState.playerStatus);
  }

  ngOnInit(): void {
    this._subscription.add(
      this._gameStartService
        .startGame({
          player1: 'player1',
          player2: 'player2',
        })
        .subscribe((gameState: GameStatus) => {
          this.diskMapSignal.set(gameState.diskMap);
          this._gameStateService.set(gameState);
          this._gameState = gameState;
        })
    );
  }

  _initializeGameState(): GameStatus {
    const diskMap = new Map<string, "LIGHT" | "DARK" | null>();
    for (const h of Object.values(HorizontalPosition)) {
      for (const v of Object.values(VerticalPosition)) {
        diskMap.set(new Position(h, v).toString(), null);
      }
    }
    const newGameState = GameStatus.of(
      'gameId',
      'player1',
      'INITIAL',
      diskMap,
    )
    this._gameStateService.set(newGameState);
    return newGameState;
  }

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }

  onSquareClick(position: Position) {
    this._subscription.add(
      this._putDiskService.putDisk(this._gameState.gameId, {
        playerNumber: this._gameState.playerStatus.nextPlayer,
        horizontalPosition: position.horizontalPosition,
        verticalPosition: position.verticalPosition,
      }).subscribe((response: PutDiskResponse) => {
        const newGameState = GameStatus.of(
          response.gameId,
          response.nextPlayer,
          response.progress,
          new Map(Object.entries(response.diskMap)),
        );
        this.diskMapSignal.set(newGameState.diskMap);
        this._gameStateService.set(newGameState);
        this._gameState = newGameState;
      })
    );
  }
}
