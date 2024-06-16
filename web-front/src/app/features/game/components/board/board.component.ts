import {Component, EventEmitter, Input, Output} from '@angular/core';
import {SquareComponent} from "../square/square.component";
import {NgForOf} from "@angular/common";
import {HorizontalPosition, Position, VerticalPosition} from "../../models/position.module";
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
  @Output() squareClicked = new EventEmitter<Position>();
  @Input() diskMap: Map<string, Disk | null> | undefined;
  columns: HorizontalPosition[] = Object.values(HorizontalPosition);
  rows: VerticalPosition[] = Object.values(VerticalPosition);

  onSquareClick(position: Position) {
    this.squareClicked.emit(position);
  }

  getPosition(horizontalPosition: HorizontalPosition, verticalPosition: VerticalPosition): Position {
    return new Position(horizontalPosition, verticalPosition);
  }

  getDisk(horizontalPosition: HorizontalPosition, verticalPosition: VerticalPosition): Disk | null {
    return this.diskMap?.get(new Position(horizontalPosition, verticalPosition).toString()) || null;
  }
}
