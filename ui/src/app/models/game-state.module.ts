import {createDiskType, Disk} from "./disk.model";
import {HorizontalPosition, Position, VerticalPosition} from "./position.model";
import {asGameProgress, GameProgress} from "./game-progress.model";
import {asBrand, Brand} from "./brand.module";

export type GameId = Brand<string, "GameId">;
export type Player = "PLAYER1" | "PLAYER2";
export type Score = Brand<number, "Score">;

export class PlayerStatus {
  nextPlayer: Player;
  player1Score: Score;
  player2Score: Score;

  constructor(nextPlayer: Player, player1Score: Score, player2Score: Score) {
    this.nextPlayer = nextPlayer;
    this.player1Score = player1Score;
    this.player2Score = player2Score;
  }

  static of(nextPlayer: Player): PlayerStatus {
    return new PlayerStatus(
      nextPlayer,
      asBrand(0, "number", "Score"),
      asBrand(0, "number", "Score"),
    )
  }
}

/**
 * ゲームの状態を表す型
 */
export class GameStatus {
  gameId: GameId;
  progress: GameProgress;
  playerStatus: PlayerStatus;
  diskMap: Map<string, Disk | null>;

  constructor(gameId: GameId, nextPlayer: Player, progress: GameProgress, diskMap: Map<string, Disk | null>) {
    this.gameId = gameId;
    this.progress = progress;
    this.playerStatus = PlayerStatus.of(nextPlayer);
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
  ): GameStatus {
    return new GameStatus(
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
