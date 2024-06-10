import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {BoardComponent} from "./features/board/pages/board/board.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, BoardComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'web-front';
}
