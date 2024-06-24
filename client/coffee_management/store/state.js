import { defineStore } from 'pinia'

export const useStateStore = defineStore('useStateStore', {
  state: () => ({
    WebTitle: 'WheelsCity'
  }),

  actions: {
    changeName(newName){
      this.WebTitle = newName
    }
  }
})
