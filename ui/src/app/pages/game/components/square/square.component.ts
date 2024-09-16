import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Position} from "../../../../models/position.model";
import {Disk, DiskType} from "../../../../models/disk.model";

@Component({
  selector: 'app-square',
  standalone: true,
  imports: [],
  templateUrl: './square.component.html',
  styleUrl: './square.component.scss'
})
export class SquareComponent {
  @Input() position!: Position;
  @Input() disk: Disk | null = null;
  @Output() squareClicked = new EventEmitter<Position>();

  onClick() {
    this.squareClicked.emit(this.position);
  }

  protected readonly DiskType = DiskType;
}
