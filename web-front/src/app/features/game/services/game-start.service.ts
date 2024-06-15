import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";

export interface GameStartRequest {
  "player1": string,
  "player2": string,
}

export interface GameStartResponse {
  gameId: string;
  player1Name: string;
  player2Name: string;
  nextPlayer: string;
  progress: string;
  diskMap: Record<string, string | 'LIGHT' | 'DARK' | null>;
}

@Injectable({
  providedIn: 'root'
})
export class GameStartService {
  private readonly _http = inject(HttpClient);

  startGame(request: GameStartRequest): Observable<GameStartResponse> {
    return this._http
      .post('/api/games/start', request)
      .pipe(
        map((response: any) => {
          return {
            gameId: response.gameId,
            player1Name: response.player1Name,
            player2Name: response.player2Name,
            nextPlayer: response.nextPlayer,
            progress: response.progress,
            diskMap: response.diskMap
          };
        })
      );
  }
}
