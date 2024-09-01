import {Component, inject} from '@angular/core';
import {GameStateService} from "../models/game-state.service";
import {Observable} from "rxjs";
import {GameState} from "../models/game-state.model";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [
    AsyncPipe,
    NgForOf,
    NgIf
  ],
  templateUrl: './game.component.html',
  styleUrl: './game.component.scss'
})
export class GameComponent {
  private _gameStateService = inject(GameStateService);
  _gameState$: Observable<GameState  | null>;

  constructor() {
    this._gameState$ = this._gameStateService.getGameState();
  }

  diskMapArray(diskMap: Record<string, 'LIGHT' | 'DARK' | null>): Array<{ key: string, value: string | null }> {
    return Object.keys(diskMap).map(key => ({
      key,
      value: diskMap[key]
    }));
  }
}
