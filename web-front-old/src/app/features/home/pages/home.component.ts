import {Component, inject} from '@angular/core';
import {Subscription} from "rxjs";
import {GameStartResponse, GameStartService} from "../../game/services/game-start.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  constructor(private _router: Router) {
  }
  private _gameStartService = inject(GameStartService);
  private _subscription: Subscription = new Subscription();

  startGame() {
    console.log("startGame");
    this._router.navigate(['/game']);
    // this._subscription.add(
    //   this._gameStartService
    //     .startGame({
    //       player1: 'player1',
    //       player2: 'player2',
    //     })
    //     .subscribe({
    //       next: () => {
    //         console.log("ゲームを開始しました。 ");
    //       },
    //       error: (error) => {
    //         console.log("ゲーム開始に失敗しました。 ", error);
    //       },
    //     })
    // );
  }
}
