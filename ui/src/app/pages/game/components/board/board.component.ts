import {Component, EventEmitter, Input, Output, Signal} from '@angular/core';
import {SquareComponent} from "../square/square.component";
import {HorizontalPosition, Position, VerticalPosition} from "../../../../models/position.model";
import {Disk} from "../../../../models/disk.model";

@Component({
  selector: 'app-board',
  standalone: true,
  imports: [
    SquareComponent,
  ],
  templateUrl: './board.component.html',
  styleUrl: './board.component.scss'
})
export class BoardComponent {
  @Output()
  squareClicked = new EventEmitter<Position>();
  @Input() diskMapSignal!: Signal<Map<string, Disk | null>>;

  columns: HorizontalPosition[] = Object.values(HorizontalPosition);
  rows: VerticalPosition[] = Object.values(VerticalPosition);

  onSquareClick(position: Position) {
    this.squareClicked.emit(position);
  }

  getPosition(horizontalPosition: HorizontalPosition, verticalPosition: VerticalPosition): Position {
    return new Position(horizontalPosition, verticalPosition);
  }

  getDisk(horizontalPosition: HorizontalPosition, verticalPosition: VerticalPosition): Disk | null {
    if (!this.diskMapSignal) {
      return null;
    }
    return this.diskMapSignal()?.get(new Position(horizontalPosition, verticalPosition).toString()) || null;
  }
}
