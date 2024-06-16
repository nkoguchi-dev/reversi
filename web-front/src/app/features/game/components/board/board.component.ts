import {Component, EventEmitter, Input, Output} from '@angular/core';
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
  private _diskMap: Map<string, Disk | null> | undefined;
  positions: Position[] | null = null;
  @Output() squareClicked = new EventEmitter<Position>();

  @Input()
  set diskMap(diskMap: Map<string, Disk | null> | undefined) {
    this._diskMap = diskMap;
    this.positions = this.diskMapToArray();
  }

  onSquareClick(position: Position) {
    this.squareClicked.emit(position);
  }

  // squareコンポーネントを並べるためにdiskMapを配列にして返す
  diskMapToArray(): Position[] | null {
    if (this._diskMap === undefined) {
      return null;
    }
    return Array.from(this._diskMap.keys())
      .sort((posA, posB) => {
        // Gridの左上から右下に向かって並べるため、vertical, horizontalの順で比較してsortする
        const [horizontalA, verticalA] = posA.split(':');
        const [horizontalB, verticalB] = posB.split(':');
        const verticalComparison = verticalA.localeCompare(verticalB);
        if (verticalComparison !== 0) {
          return verticalComparison;
        }
        return horizontalA.localeCompare(horizontalB);
      })
      .map(Position.of);
  }

  getDisk(position: Position): Disk | null {
    return this._diskMap?.get(position.toString()) || null;
  }
}
