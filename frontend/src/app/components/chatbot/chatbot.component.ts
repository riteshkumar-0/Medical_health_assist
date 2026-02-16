import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';

interface ChatMessage {
  text: string;
  sender: 'user' | 'bot';
  timestamp: Date;
}

@Component({
  selector: 'app-chatbot',
  templateUrl: './chatbot.component.html',
  styleUrls: ['./chatbot.component.css']
})
export class ChatbotComponent implements OnInit {
  isOpen = false;
  userMessage = '';
  messages: ChatMessage[] = [];
  isLoading = false;
  sessionId: string | null = null;

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.messages.push({
      text: 'Hello! I\'m your Medical Information Assistant. Ask me anything about health products, medical devices, or wellness tips!',
      sender: 'bot',
      timestamp: new Date()
    });
  }

  toggleChat(): void {
    this.isOpen = !this.isOpen;
    if (this.isOpen) {
      document.body.style.overflow = 'hidden';
    } else {
      document.body.style.overflow = '';
    }
  }

  closeChat(): void {
    this.isOpen = false;
    document.body.style.overflow = '';
  }

  sendMessage(): void {
    const msg = this.userMessage.trim();
    if (!msg || this.isLoading) return;

    const textarea = document.querySelector('.chat-text-input') as HTMLTextAreaElement;
    if (textarea) textarea.style.height = 'auto';

    this.messages.push({
      text: msg,
      sender: 'user',
      timestamp: new Date()
    });

    this.userMessage = '';
    this.isLoading = true;

    setTimeout(() => this.scrollToBottom(), 50);

    this.apiService.chatbotChat(msg, this.sessionId).subscribe({
      next: (res) => {
        this.sessionId = res.sessionId;
        this.messages.push({
          text: res.reply,
          sender: 'bot',
          timestamp: new Date()
        });
        this.isLoading = false;
        setTimeout(() => this.scrollToBottom(), 50);
      },
      error: () => {
        this.messages.push({
          text: 'Sorry, I encountered an error. Please try again.',
          sender: 'bot',
          timestamp: new Date()
        });
        this.isLoading = false;
        setTimeout(() => this.scrollToBottom(), 50);
      }
    });
  }

  onKeyDown(event: KeyboardEvent): void {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault();
      this.sendMessage();
    }
  }

  autoResize(event: Event): void {
    const textarea = event.target as HTMLTextAreaElement;
    textarea.style.height = 'auto';
    textarea.style.height = Math.min(textarea.scrollHeight, 140) + 'px';
  }

  private scrollToBottom(): void {
    const body = document.querySelector('.chat-body');
    if (body) {
      body.scrollTop = body.scrollHeight;
    }
  }
}