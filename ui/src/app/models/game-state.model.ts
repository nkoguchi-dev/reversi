export interface GameState {
  gameId: string;
  player1Name: string;
  player2Name: string;
  nextPlayer: string;
  progress: string;
  diskMap: Record<string, 'LIGHT' | 'DARK' | null>;
}
