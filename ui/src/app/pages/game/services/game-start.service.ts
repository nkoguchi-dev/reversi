import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {GameStatus} from "../../../models/game-state.module";

export interface GameStartRequest {
  "player1": string,
  "player2": string,
}

export interface GameStartResponse {
  gameId: string;
  player1Status: PlayerStatus;
  player2Status: PlayerStatus;
  nextPlayer: 'PLAYER1' | 'PLAYER2';
  progress: string;
  diskMap: Map<string, 'LIGHT' | 'DARK' | null>;
}

export interface PlayerStatus {
  name: string;
  score: number;
}

@Injectable({
  providedIn: 'root'
})
export class GameStartService {
  private readonly _http = inject(HttpClient);

  startGame(request: GameStartRequest): Observable<GameStatus> {
    return this._http
      .post<GameStartResponse>('/api/games/start', request)
      .pipe(
        map(response => {
          const diskMap = new Map<string, 'LIGHT' | 'DARK' | null>(Object.entries(response.diskMap));
          return GameStatus.of(
            response.gameId,
            response.nextPlayer,
            response.player1Status.score,
            response.player2Status.score,
            response.progress,
            diskMap,
          )
        })
      );
  }
}
