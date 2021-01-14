<template>
  <v-app>
    <v-navigation-drawer absolute temporary v-model="drawerToggle">
    </v-navigation-drawer>
    <v-app-bar app class="light-blue darken-1">
      <v-app-bar-nav-icon @click.native.stop="drawerToggle = !drawerToggle"></v-app-bar-nav-icon>
      <v-toolbar-title>
        Vladex
      </v-toolbar-title>
      <v-spacer></v-spacer>
    </v-app-bar>
    <v-content>
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
export default {
  data() {
    return {
      drawerToggle: false
    }
  }
}
</script>