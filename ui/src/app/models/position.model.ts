/**
 * ディスクの位置を表す型
 */
export class Position {
  horizontalPosition: HorizontalPosition;
  verticalPosition: VerticalPosition;

  constructor(horizontalPosition: HorizontalPosition, verticalPosition: VerticalPosition) {
    this.horizontalPosition = horizontalPosition;
    this.verticalPosition = verticalPosition;
  }

  /**
   * F:4のような形式の文字列から Position インスタンスを生成する
   * @param positionString
   */
  static of(positionString: string): Position {
    const positions = positionString.split(':');
    if (positions.length !== 2) {
      throw new Error(`Invalid position string: ${positionString}`);
    }
    return new Position(
      asHorizontalPosition(positions[0]),
      asVerticalPosition(positions[1]),
    );
  }

  toString(): string {
    return `${this.horizontalPosition}:${this.verticalPosition}`;
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
function isHorizontalPosition(value: string): value is HorizontalPosition {
  return Object.values(HorizontalPosition).includes(value as HorizontalPosition);
}

function asHorizontalPosition(value: string): HorizontalPosition {
  if (!isHorizontalPosition(value)) {
    throw new Error(`Invalid horizontal position: ${value}`);
  }
  return value;
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

function isVerticalPosition(value: string): value is VerticalPosition {
  return Object.values(VerticalPosition).includes(value as VerticalPosition);
}

function asVerticalPosition(value: string): VerticalPosition {
  if (!isVerticalPosition(value)) {
    throw new Error(`Invalid vertical position: ${value}`);
  }
  return value;
}
