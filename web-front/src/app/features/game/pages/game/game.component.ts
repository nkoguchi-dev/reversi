import {Component, inject, OnDestroy} from '@angular/core';
import {GameStartResponse, GameStartService} from "../../services/game-start.service";
import {Subscription} from "rxjs";
import {GameState} from "../../models/game-state.module";
import {BoardComponent} from "../../components/board/board.component";

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

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }

  onButtonClick() {
    this._subscription.add(
      this._gameStartService
        .startGame({
          player1: 'player1',
          player2: 'player2',
        })
        .subscribe((response: GameStartResponse) => {
          const gameState = GameState.of(
            response.gameId,
            response.nextPlayer,
            response.progress,
            new Map(Object.entries(response.diskMap)),
          );
          console.log(`gameId: ${gameState.gameId}`);
          console.log(`nextPlayer: ${gameState.nextPlayer}`);
          console.log(`progress: ${gameState.progress}`);
          gameState.diskMap.forEach((diskType, position) => {
              console.log(`position: ${position.horizontalPosition}:${position.verticalPosition}`);
              console.log(`diskType: ${diskType}`);
            });
        })
    );
  }
}
