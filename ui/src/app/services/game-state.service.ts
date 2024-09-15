import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {GameState} from "../models/game-state.module";

@Injectable({
  providedIn: 'root'
})
export class GameStateService {
  private gameStateSubject = new BehaviorSubject<GameState | null>(null);

  constructor() {
  }

  set(state: GameState) {
    this.gameStateSubject.next(state)
  }

  get(): Observable<GameState | null> {
    return this.gameStateSubject.asObservable();
  }
}
