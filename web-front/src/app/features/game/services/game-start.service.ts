import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

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
  diskMap: Record<string, 'LIGHT' | 'DARK' | null>;
}

@Injectable({
  providedIn: 'root'
})
export class GameStartService {
  private readonly _http = inject(HttpClient);

  startGame(request: GameStartRequest): Observable<GameStartResponse> {
    return this._http.post<GameStartResponse>('/api/games/start', request);
  }
}
