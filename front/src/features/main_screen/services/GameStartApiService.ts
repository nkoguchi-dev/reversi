import {AxiosResponse} from 'axios';
import apiClient from '../../../services/ApiService';

export interface GameStartRequest {
    player1: string;
    player2: string;
}

export interface GameStartResponse {
    gameId: string;
    player1Name: string;
    player2Name: string;
    nextPlayer: string;
    progress: string;
    diskMap: Record<string, 'LIGHT' | 'DARK' | null>;
}

export class GameStartService {
    async startGame(request: GameStartRequest): Promise<GameStartResponse> {
        const response: AxiosResponse<GameStartResponse> = await apiClient.post('/api/games/start', request);
        return response.data;
    }
}