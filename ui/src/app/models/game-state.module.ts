import {createDiskType, Disk} from "./disk.model";
import {HorizontalPosition, Position, VerticalPosition} from "./position.model";
import {asGameProgress, GameProgress} from "./game-progress.model";
import {asBrand, Brand} from "./brand.module";

export type GameId = Brand<string, "GameId">
export type Player = "player1" | "player2";

/**
 * ゲームの状態を表す型
 */
export class GameState {
  gameId: GameId;
  nextPlayer: Player;
  progress: GameProgress;
  diskMap: Map<string, Disk | null>;

  constructor(gameId: GameId, nextPlayer: Player, progress: GameProgress, diskMap: Map<string, Disk | null>) {
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
    nextPlayer: Player,
    progress: string,
    diskMap: Map<string, 'LIGHT' | 'DARK' | null>,
  ): GameState {
    return new GameState(
      asBrand(gameId, "string", "GameId"),
      nextPlayer,
      asGameProgress(progress),
      new Map(
        Array.from(diskMap.entries())
          .map(([positionString, diskTypeString]) => {
            return [
              positionString,
              diskTypeString !== null
                ? new Disk(createDiskType(diskTypeString))
                : null
            ]
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
