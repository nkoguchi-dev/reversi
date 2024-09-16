import {Component, Input, Signal} from '@angular/core';
import {Player, PlayerStatus, Score} from "../../../../models/game-state.module";
import {asBrand} from "../../../../models/brand.module";

@Component({
  selector: 'app-player-status',
  standalone: true,
  imports: [],
  templateUrl: './player-status.component.html',
  styleUrl: './player-status.component.scss'
})
export class PlayerStatusComponent {
  @Input()
  playerStatusSignal!: Signal<PlayerStatus>;
  @Input()
  player: Player | undefined;

  getPlayerScore(): Score {
    if (!this.playerStatusSignal) {
      return asBrand(0, "number", "Score");
    }
    if (this.player === 'player1') {
      return this.playerStatusSignal().player1Score;
    } else {
      return this.playerStatusSignal().player2Score;
    }
  }
}
