import {RouterModule, Routes} from '@angular/router';
import {GameComponent} from "./features/game/pages/game/game.component";
import {HomeComponent} from "./features/home/pages/home.component";
import {NgModule} from "@angular/core";

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'game', component: GameComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
