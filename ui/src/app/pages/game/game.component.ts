import {Component, inject, OnDestroy, OnInit, signal} from '@angular/core';
import {AsyncPipe} from "@angular/common";
import {Subscription} from "rxjs";
import {DebugComponent} from "./components/debug/debug.component";
import {BoardComponent} from "./components/board/board.component";
import {GameStartService} from "../home/services/game-start.service";
import {Disk} from "../../models/disk.model";
import {HorizontalPosition, Position, VerticalPosition} from "../../models/position.model";
import {GameState} from "../../models/game-state.module";
import {PutDiskResponse, PutDiskService} from "./services/put-disk.service";

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [
    AsyncPipe,
    DebugComponent,
    BoardComponent
  ],
  templateUrl: './game.component.html',
  styleUrl: './game.component.scss'
})
export class GameComponent implements OnInit, OnDestroy {
  private readonly _gameStartService = inject(GameStartService);
  private readonly _putDiskService = inject(PutDiskService);
  private readonly _subscription: Subscription = new Subscription();
  gameState: GameState;
  readonly diskMapSignal = signal<Map<string, Disk | null>>(new Map());

  constructor() {
    this.gameState = this._initializeGameState();
    // 版目の初期化処理。どこかのクラスに移したい。
    const diskMap = new Map<string, Disk | null>();
    for (const h of Object.values(HorizontalPosition)) {
      for (const v of Object.values(VerticalPosition)) {
        diskMap.set(new Position(h, v).toString(), null);
      }
    }
    this.diskMapSignal.set(diskMap);
  }

  ngOnInit(): void {
    this._subscription.add(
      this._gameStartService
        .startGame({
          player1: 'player1',
          player2: 'player2',
        })
        .subscribe((gameState: GameState) => {
          this.diskMapSignal.set(gameState.diskMap);
          this.gameState = gameState;
        })
    );
  }

  _initializeGameState(): GameState {
    const diskMap = new Map<string, Disk | null>();
    for (const h of Object.values(HorizontalPosition)) {
      for (const v of Object.values(VerticalPosition)) {
        diskMap.set(new Position(h, v).toString(), null);
      }
    }
    return new GameState(
      'gameId',
      'nextPlayer',
      'INITIAL',
      diskMap,
    );
  }

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }

  onSquareClick(position: Position) {
    console.log(`onSquareClicked position: ${position.toString()}`);
    this._subscription.add(
      this._putDiskService.putDisk(this.gameState.gameId, {
        playerNumber: this.gameState.nextPlayer,
        horizontalPosition: position.horizontalPosition,
        verticalPosition: position.verticalPosition,
      }).subscribe((response: PutDiskResponse) => {
        this.gameState = GameState.of(
          response.gameId,
          response.nextPlayer,
          response.progress,
          new Map(Object.entries(response.diskMap)),
        );
        this.diskMapSignal.set(this.gameState.diskMap);
      })
    );
  }
}
