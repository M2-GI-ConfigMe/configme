<template>
  <v-card class="h-100 d-flex flex-column" style="flex: 1 1 0; overflow-y: hidden">
    <div class="pt-2 pr-2 text-right">
      <v-btn icon @click="close()"><v-icon>mdi-close</v-icon></v-btn>
    </div>
    <div>
      <v-list-item two-line>
        <v-img max-height="300" max-width="300" :src="component.url" class="mr-2 mt-2"> </v-img>
        <v-list-item-content>
          <v-row>
            <v-col>
              <v-list-item-title class="text-h4">{{ component.name }}</v-list-item-title>
              <v-list-item-subtitle>{{ component.name }}</v-list-item-subtitle>
            </v-col>
            <v-col align-self="center" class="text-h5 justify-content-end d-flex">
              <span class="font-weight-bold">{{ component.price }}€</span>
            </v-col>
          </v-row>
        </v-list-item-content>
      </v-list-item>
    </div>
    <div style="min-height: 0">
      <v-card-text class="px-0 pb-0 d-flex flex-column h-100" style="min-height: 0">
        <span class="ml-2 font-weight-bold text-dark">Informations supplémentaires</span>
        <p class="mt-2 px-4 text-dark">{{ component.desc }}</p>
        <v-divider class="my-0"></v-divider>
        <v-simple-table style="min-height: 0; overflow-y: auto">
          <template v-slot:default>
            <tbody>
              <tr v-for="[key, value] in Object.entries(filteredComponent)" :key="key">
                <td><span v-text="$t('configmeApp.' + componentName.replace(/[0-9]/g, '') + '.' + key)"></span></td>
                <td v-if="key == 'dimension'">(L x l x H) {{ value.length }}mm x {{ value.width }}mm x {{ value.height }}mm</td>
                <td v-else-if="key == 'hasGpu' || key == 'hasVentirad' || key == 'hasThermalPaste'">
                  <v-icon :color="value > 0 ? 'success' : 'red'">{{ value ? 'mdi-check' : 'mdi-close' }}</v-icon>
                </td>
                <td v-else-if="key == 'sizeMaxGpu'">{{ value }}mm</td>
                <td v-else-if="key == 'sizeMaxPsu'">{{ value }}mm</td>
                <td v-else-if="key == 'sizeMaxVentirad'">{{ value }}mm</td>
                <td v-else-if="key == 'ramFrequencymax'">{{ value }}MHz</td>
                <td v-else-if="key == 'ramSizeMax'">{{ value }}Go</td>
                <td v-else-if="key == 'unitSize'">{{ value }}Go</td>
                <td v-else-if="key == 'noise'">{{ value }}Db</td>
                <td v-else-if="key == 'lithography'">{{ value }}nm</td>
                <td v-else-if="key == 'capacity'">{{ value }}Go</td>
                <td v-else-if="key == 'speedWrite'">{{ value }}Go/s</td>
                <td v-else-if="key == 'speedRead'">{{ value }}Go/s</td>
                <td v-else-if="key == 'power'">{{ value }}W</td>
                <td v-else-if="key == 'cacheL1' || key == 'cacheL2' || key == 'cacheL3'">{{ value }}Mo</td>
                <td v-else-if="key == 'frequency' && componentName.replace(/[0-9]/g, '') == 'ram'">{{ value }}Mhz</td>
                <td v-else-if="key == 'frequency' && componentName == 'cpu'">{{ value }}Ghz</td>
                <td v-else-if="key == 'frequency' && componentName == 'gpu'">{{ value }}Mhz</td>
                <td v-else>{{ value }}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-card-text>
    </div>
  </v-card>
</template>

<script lang="ts" src="./component-details.component.ts"></script>
