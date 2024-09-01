import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GameStateService {
  private gameState: any;

  constructor() {
  }

  setGameState(state: any) {
    this.gameState = state;
  }

  getGameState(): any {
    return this.gameState;
  }
}
