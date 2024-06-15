import {createDiskType, DiskType} from "./disk.module";
import {Position} from "./position.module";

/**
 * ゲームの状態を表す型
 */
export class GameState {
  gameId: string;
  nextPlayer: string;
  progress: string;
  diskMap: Map<Position, DiskType | null>;

  constructor(gameId: string, nextPlayer: string, progress: string, diskMap: Map<Position, DiskType | null>) {
    this.gameId = gameId;
    this.nextPlayer = nextPlayer;
    this.progress = progress;
    this.diskMap = diskMap;
  }

  static of(
    gameId: string,
    nextPlayer: string,
    progress: string,
    diskMap: Map<string, 'LIGHT' | 'DARK' | null>,
  ): GameState {
    return new GameState(
      gameId,
      nextPlayer,
      progress,
      new Map(
        Array.from(diskMap.entries())
          .map(([positionString, diskTypeString]) => {
            return [Position.of(positionString), createDiskType(diskTypeString)]
          })
      )
    )
  }
}
