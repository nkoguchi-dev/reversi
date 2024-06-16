import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Position} from "../../models/position.module";
import {Disk, DiskType} from "../../models/disk.module";

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
