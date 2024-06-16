import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

export interface PutDiskRequest {
  playerNumber: string;
  horizontalPosition: string;
  verticalPosition: string;
}

export interface PutDiskResponse {
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
export class PutDiskService {
  private readonly _http = inject(HttpClient);

  putDisk(gameId: string, request: PutDiskRequest): Observable<PutDiskResponse> {
    return this._http.put<PutDiskResponse>(`/api/games/${gameId}`, request);
  }
}
