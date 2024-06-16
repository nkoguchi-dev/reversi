import {Component, inject, OnDestroy} from '@angular/core';
import {GameStartResponse, GameStartService} from "../../services/game-start.service";
import {Subscription} from "rxjs";
import {GameState} from "../../models/game-state.module";
import {BoardComponent} from "../../components/board/board.component";
import {HorizontalPosition, Position, VerticalPosition} from "../../models/position.module";
import {Disk} from "../../models/disk.module";

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [
    BoardComponent
  ],
  templateUrl: './game.component.html',
  styleUrl: './game.component.scss'
})
export class GameComponent implements OnDestroy {
  private _gameStartService = inject(GameStartService);
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
      'progress',
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
}
