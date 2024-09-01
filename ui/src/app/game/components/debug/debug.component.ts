import {Component, inject} from '@angular/core';
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {GameStateService} from "../../../models/game-state.service";
import {Observable} from "rxjs";
import {GameState} from "../../../models/game-state.model";

@Component({
  selector: 'app-debug',
  standalone: true,
    imports: [
        AsyncPipe,
        NgForOf,
        NgIf
    ],
  templateUrl: './debug.component.html',
  styleUrl: './debug.component.scss'
})
export class DebugComponent {
  private _gameStateService = inject(GameStateService);
  gameState$: Observable<GameState  | null>;

  constructor() {
    this.gameState$ = this._gameStateService.getGameState();
  }

  diskMapArray(diskMap: Record<string, 'LIGHT' | 'DARK' | null>): Array<{ key: string, value: string | null }> {
    return Object.keys(diskMap).map(key => ({
      key,
      value: diskMap[key]
    }));
  }
}
