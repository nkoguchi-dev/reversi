import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-square',
  standalone: true,
  imports: [],
  templateUrl: './square.component.html',
  styleUrl: './square.component.scss'
})
export class SquareComponent {
  @Input() value: string | null = null;
  @Output() squareClicked = new EventEmitter<void>();

  onClick() {
    this.squareClicked.emit();
  }
}
