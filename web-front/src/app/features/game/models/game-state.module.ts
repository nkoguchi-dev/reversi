import {createDiskType, Disk} from "./disk.module";
import {HorizontalPosition, Position, VerticalPosition} from "./position.module";

/**
 * ゲームの状態を表す型
 */
export class GameState {
  gameId: string;
  nextPlayer: string;
  progress: string;
  diskMap: Map<string, Disk | null>;

  constructor(gameId: string, nextPlayer: string, progress: string, diskMap: Map<string, Disk | null>) {
    this.gameId = gameId;
    this.nextPlayer = nextPlayer;
    this.progress = progress;
    this.diskMap = initializeDiskMap();
    // diskMapの内容を反映
    for (const [position, disk] of diskMap) {
      this.diskMap.set(position, disk);
    }
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
            return [positionString, new Disk(createDiskType(diskTypeString))]
          })
      )
    )
  }
}

function initializeDiskMap(): Map<string, Disk | null> {
  const diskMap = new Map<string, Disk | null>();

  // HorizontalPosition と VerticalPosition のすべての組み合わせを生成
  for (const hPos of Object.values(HorizontalPosition)) {
    for (const vPos of Object.values(VerticalPosition)) {
      const position = new Position(hPos, vPos);
      diskMap.set(position.toString(), null);
    }
  }

  return diskMap;
}
