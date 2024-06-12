import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {GameComponent} from "./features/game/pages/game/game.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, GameComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'web-front';
}
