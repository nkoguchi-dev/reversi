import {Component, Input, Signal} from '@angular/core';
import {GameProgress} from "../../../../models/game-progress.model";
import {NgClass} from "@angular/common";

@Component({
  selector: 'app-game-status',
  standalone: true,
  imports: [
    NgClass
  ],
  templateUrl: './game-status.component.html',
  styleUrl: './game-status.component.scss'
})
export class GameStatusComponent {
  @Input()
  gameStatusSignal!: Signal<GameProgress>;

  gameStatus(): string {
    if (!this.gameStatusSignal) {
      return "";
    }
    const progress = this.gameStatusSignal();
    if (progress === GameProgress.IN_PROGRESS || progress === GameProgress.CREATED) {
      return "in_progress";
    } else if (progress === GameProgress.INITIAL) {
      return "initial";
    } else if (progress === GameProgress.FINISHED) {
      return "finished";
    } else {
      return "";
    }
  }

  gameStatusMessage(): string {
    if (!this.gameStatusSignal) {
      return "";
    }
    const progress = this.gameStatusSignal();
    if (progress === GameProgress.IN_PROGRESS || progress === GameProgress.CREATED) {
      return "ゲーム進行中";
    } else if (progress === GameProgress.INITIAL) {
      return "";
    } else if (progress === GameProgress.FINISHED) {
      return "ゲーム終了";
    } else {
      return "";
    }
  }
}
