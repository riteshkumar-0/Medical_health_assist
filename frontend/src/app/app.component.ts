import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <app-navbar></app-navbar>
    <main class="main-content">
      <router-outlet></router-outlet>
    </main>
    <app-footer></app-footer>
    <app-chatbot></app-chatbot>
  `,
  styles: [`
    .main-content {
      padding-top: 76px;
      min-height: 100vh;
      background: linear-gradient(135deg, #f5f7fa 0%, #e4e8f0 100%);
    }
  `]
})
export class AppComponent {
  title = 'Medical Information Assistant';
}
