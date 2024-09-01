import {Component, inject} from '@angular/core';
import {Router} from "@angular/router";
import {GameStateService} from "../models/game-state.service";
import {GameStartService} from "./services/game-start.service";
import {Subscription} from "rxjs";
import {GameState} from "../models/game-state.model";

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
        .subscribe((gameState: GameState) => {
          this._gameStateService.setGameState(gameState);
          this._router.navigate(['/game']);
        })
    );
  }
}
