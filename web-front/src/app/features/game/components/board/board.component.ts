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
  private _diskMap: Map<string, Disk | null> | undefined;
  positions: Position[] | null = null;

  @Input()
  set diskMap(diskMap: Map<string, Disk | null> | undefined) {
    this._diskMap = diskMap;
    this.positions = this.diskMapToArray();
  }

  onSquareClick(position: Position) {
    console.log('Square clicked', position);
  }

  // squareコンポーネントを並べるためにdiskMapを配列にして返す
  diskMapToArray(): Position[] | null {
    if (this._diskMap === undefined) {
      return null;
    }
    return Array.from(this._diskMap.keys()).map(Position.of);
  }

  getDisk(position: Position): Disk | null {
    return this._diskMap?.get(position.toString()) || null;
  }
}
