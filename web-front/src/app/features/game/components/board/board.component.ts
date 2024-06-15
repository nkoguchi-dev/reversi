import { Component } from '@angular/core';
import {SquareComponent} from "../square/square.component";
import {NgForOf} from "@angular/common";

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
  squares: (string | null)[] = Array(64).fill(null);

  onSquareClick(index: number) {
    if (!this.squares[index]) {
      this.squares[index] = 'X'; // プレイヤーの動きを記録するための仮のコード
    }
  }
}
