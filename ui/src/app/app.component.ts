import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {GameComponent} from "./pages/game/game.component";

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterOutlet, HomeComponent, GameComponent],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss'
})
export class AppComponent {
    title = 'ui';
}
