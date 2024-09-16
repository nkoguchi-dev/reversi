import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {GameStatus} from "../../../models/game-state.module";

export interface PutDiskRequest {
  playerNumber: string;
  horizontalPosition: string;
  verticalPosition: string;
}

export interface PutDiskResponse {
  gameId: string;
  player1Status: PlayerStatus;
  player2Status: PlayerStatus;
  nextPlayer: 'PLAYER1' | 'PLAYER2';
  progress: string;
  diskMap: Record<string, 'LIGHT' | 'DARK' | null>;
}

export interface PlayerStatus {
  name: string;
  score: number;
}


@Injectable({
  providedIn: 'root'
})
export class PutDiskService {
  private readonly _http = inject(HttpClient);

  putDisk(gameId: string, request: PutDiskRequest): Observable<GameStatus> {
    return this._http
      .put<PutDiskResponse>(`/api/games/${gameId}`, request)
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
