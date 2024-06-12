import {Component, inject, OnDestroy} from '@angular/core';
import {GameStartResponse, GameStartService} from "../../services/game-start.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [],
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
          console.log(response);
        })
    );
  }
}
