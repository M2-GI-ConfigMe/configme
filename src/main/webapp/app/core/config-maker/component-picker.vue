<template>
  <div>
    <v-dialog v-model="show" fullscreen transition="slide-x-reverse-transition" content-class="component-picker" @click:outside.stop>
      <v-card>
        <v-toolbar dark color="primary">
          <v-btn icon dark @click="activated = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>{{ this.displayName }}</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-toolbar-items> </v-toolbar-items>
        </v-toolbar>
        <v-card-text>
          <v-data-table
            :headers="headers"
            :items="data.objects"
            :options.sync="options"
            :server-items-length="dataLength"
            :loading="loading"
            loading-text="Loading... Please wait"
            class="elevation-1"
            @click:row="handleRowClick"
          ></v-data-table>
        </v-card-text>
      </v-card>
    </v-dialog>
    <v-overlay :value="show"> </v-overlay>
  </div>
</template>

<script lang="ts" src="./component-picker.component.ts"></script>

<style>
.component-picker {
  width: 50%;
  right: 0;
  left: auto;
}
</style>
