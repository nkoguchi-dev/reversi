import {DiskType} from "./disk.module";

/**
 * ゲームの状態を表す型
 */
export class GameState {
  gameId: string;
  nextPlayer: string;
  progress: string;
  diskMap: {
    Position: DiskType | null;
  }

  constructor(gameId: string, nextPlayer: string, progress: string, diskMap: { Position: DiskType | null }) {
    this.gameId = gameId;
    this.nextPlayer = nextPlayer;
    this.progress = progress;
    this.diskMap = diskMap;
  }
}
