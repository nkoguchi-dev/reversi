import {Component, inject} from '@angular/core';
import {Router} from "@angular/router";
import {GameStateService} from "../game/services/game-state.service";
import {GameStartService} from "./services/game-start.service";
import {GameStartResponse} from "./services/game-start.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  private _router = inject(Router);
  private _gameStateService = inject(GameStateService);
  private _gameStartService = inject(GameStartService);
  private _subscription: Subscription = new Subscription();

  startGame() {
    this._subscription.add(
      this._gameStartService
        .startGame({
          player1: 'player1',
          player2: 'player2',
        })
        .subscribe((response: GameStartResponse) => {
          this._gameStateService.setGameState(response);
          this._router.navigate(['/game']);
        })
    );
  }
}
