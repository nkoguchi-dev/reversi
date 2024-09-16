import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {GameStatus} from "../models/game-state.module";

@Injectable({
  providedIn: 'root'
})
export class GameStateService {
  private gameStateSubject = new BehaviorSubject<GameStatus | null>(null);

  constructor() {
  }

  set(state: GameStatus) {
    this.gameStateSubject.next(state)
  }

  get(): Observable<GameStatus | null> {
    return this.gameStateSubject.asObservable();
  }
}
