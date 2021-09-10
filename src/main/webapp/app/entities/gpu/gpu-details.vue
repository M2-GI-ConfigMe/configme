<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="gpu">
        <h2 class="jh-entity-heading" data-cy="gpuDetailsHeading">
          <span v-text="$t('configmeApp.gpu.detail.title')">Gpu</span> {{ gpu.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('configmeApp.gpu.frequency')">Frequency</span>
          </dt>
          <dd>
            <span>{{ gpu.frequency }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.gpu.memory')">Memory</span>
          </dt>
          <dd>
            <span>{{ gpu.memory }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.gpu.consumption')">Consumption</span>
          </dt>
          <dd>
            <span>{{ gpu.consumption }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.gpu.clockSpeed')">Clock Speed</span>
          </dt>
          <dd>
            <span>{{ gpu.clockSpeed }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.gpu.lithography')">Lithography</span>
          </dt>
          <dd>
            <span>{{ gpu.lithography }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.gpu.output')">Output</span>
          </dt>
          <dd>
            <span>{{ gpu.output }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.gpu.inputPower')">Input Power</span>
          </dt>
          <dd>
            <span>{{ gpu.inputPower }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.gpu.bus')">Bus</span>
          </dt>
          <dd>
            <span v-text="$t('configmeApp.BusType.' + gpu.bus)">{{ gpu.bus }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.gpu.dimension')">Dimension</span>
          </dt>
          <dd>
            <div v-if="gpu.dimension">
              <router-link :to="{ name: 'DimensionView', params: { dimensionId: gpu.dimension.id } }">{{ gpu.dimension.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="gpu.id" :to="{ name: 'GpuEdit', params: { gpuId: gpu.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./gpu-details.component.ts"></script>
