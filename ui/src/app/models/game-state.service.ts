import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {GameState} from "./game-state.model";

@Injectable({
  providedIn: 'root'
})
export class GameStateService {
  private gameStateSubject = new BehaviorSubject<GameState | null>(null);

  constructor() {
  }

  setGameState(state: GameState) {
    this.gameStateSubject.next(state)
  }

  getGameState(): Observable<GameState | null> {
    return this.gameStateSubject.asObservable();
  }
}
