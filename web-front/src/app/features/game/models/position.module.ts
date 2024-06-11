/**
 * ディスクの位置を表す型
 */
export class Position {
  horizontalPosition: HorizontalPosition;
  verticalPosition: VerticalPosition;

  constructor(horizontalPositionString: string, verticalPositionString: string) {
    this.horizontalPosition = HorizontalPosition[horizontalPositionString as keyof typeof HorizontalPosition];
    this.verticalPosition = VerticalPosition[verticalPositionString as keyof typeof VerticalPosition];
  }
}

/**
 * 水平方向の位置を表す型
 */
export enum HorizontalPosition {
  A = 'A',
  B = 'B',
  C = 'C',
  D = 'D',
  E = 'E',
  F = 'F',
  G = 'G',
  H = 'H',
}

/**
 * 垂直方向の位置を表す型
 */
export enum VerticalPosition {
  ONE = '1',
  TWO = '2',
  THREE = '3',
  FOUR = '4',
  FIVE = '5',
  SIX = '6',
  SEVEN = '7',
  EIGHT = '8',
}
