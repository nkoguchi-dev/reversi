import {Component, inject, OnDestroy} from '@angular/core';
import {GameStartResponse, GameStartService} from "../../services/game-start.service";
import {Subscription} from "rxjs";
import {GameState} from "../../models/game-state.module";
import {BoardComponent} from "../../components/board/board.component";
import {HorizontalPosition, Position, VerticalPosition} from "../../models/position.module";
import {Disk} from "../../models/disk.module";
import {SquareComponent} from "../../components/square/square.component";
import {PutDiskResponse, PutDiskService} from "../../services/put-disk.service";
import {GameProgress} from "../../models/game-progress.module";

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [
    BoardComponent,
    SquareComponent
  ],
  templateUrl: './game.component.html',
  styleUrl: './game.component.scss'
})
export class GameComponent implements OnDestroy {
  private _gameStartService = inject(GameStartService);
  private _putDiskService = inject(PutDiskService);
  private _subscription: Subscription = new Subscription();
  gameState: GameState = this._initializeGameState();

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
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

  onButtonClick() {
    this._subscription.add(
      this._gameStartService
        .startGame({
          player1: 'player1',
          player2: 'player2',
        })
        .subscribe((response: GameStartResponse) => {
          this.gameState = GameState.of(
            response.gameId,
            response.nextPlayer,
            response.progress,
            new Map(Object.entries(response.diskMap)),
          );
        })
    );
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
      })
    );
  }

  getGameProgress(): string {
    switch (this.gameState.progress) {
      case GameProgress.INITIAL:
        return 'ゲーム開始待ち';
      case GameProgress.CREATED:
        return 'ゲーム開始';
      case GameProgress.IN_PROGRESS:
        return '進行中';
      case GameProgress.FINISHED:
        return '終了';
    }
  }
}
