export enum GameProgress {
  INITIAL = 'INITIAL',
  CREATED = 'CREATED',
  IN_PROGRESS = 'IN_PROGRESS',
  FINISHED = 'FINISHED',
}
function isGameProgress(value: string): value is GameProgress {
  return Object.values(GameProgress).includes(value as GameProgress);
}

export function asGameProgress(value: string): GameProgress {
  if (!isGameProgress(value)) {
    throw new Error(`Invalid game progress. position: ${value}`);
  }
  return value;
}
