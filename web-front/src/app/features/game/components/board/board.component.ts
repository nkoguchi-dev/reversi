import {Component, Input} from '@angular/core';
import {SquareComponent} from "../square/square.component";
import {NgForOf} from "@angular/common";
import {Position} from "../../models/position.module";
import {Disk} from "../../models/disk.module";

@Component({
  selector: 'app-board',
  standalone: true,
  imports: [
    SquareComponent,
    NgForOf
  ],
  templateUrl: './board.component.html',
  styleUrl: './board.component.scss'
})
export class BoardComponent {
  private _diskMap: Map<Position, Disk | null> | undefined;
  positions: Position[] | null = null;

  @Input()
  set diskMap(diskMap: Map<Position, Disk | null> | undefined) {
    this._diskMap = diskMap;
    this.positions = this.diskMapToArray();
  }

  onSquareClick(position: Position) {
    console.log('Square clicked', position);
  }

  // squareコンポーネントを並べるためにdiskMapを配列にして返す
  diskMapToArray(): Position[] | null {
    console.log('diskMapToArray called. diskMap:', this._diskMap);
    if (this._diskMap === undefined) {
      return null;
    }
    const entriesArray = Array.from(this._diskMap.entries());
    const sortedEntries = entriesArray.sort(([posA], [posB]) => {
      // 水平方向の位置で比較
      const horizontalComparison = posA.horizontalPosition.localeCompare(posB.horizontalPosition);
      if (horizontalComparison !== 0) {
        return horizontalComparison;
      }

      // 水平方向が同じ場合は垂直方向で比較
      return posA.verticalPosition.localeCompare(posB.verticalPosition);
    });
    return sortedEntries.map(([position]) => position);
  }

  getDisk(position: Position): Disk | null {
    return this._diskMap?.get(position) || null;
  }
}
