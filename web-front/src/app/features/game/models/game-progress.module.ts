export enum GameProgress {
  INITIAL = 'INITIAL',
  CREATED = 'CREATED',
  IN_PROGRESS = 'IN_PROGRESS',
  FINISHED = 'FINISHED',
}
function isHorizontalPosition(value: string): value is GameProgress {
  return Object.values(GameProgress).includes(value as GameProgress);
}

export function createGameProgress(value: string): GameProgress {
  if (!isHorizontalPosition(value)) {
    throw new Error(`Invalid game progress. position: ${value}`);
  }
  return value;
}
