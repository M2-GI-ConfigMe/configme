<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="computerCase">
        <h2 class="jh-entity-heading" data-cy="computerCaseDetailsHeading">
          <span v-text="$t('configmeApp.computerCase.detail.title')">ComputerCase</span> {{ computerCase.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('configmeApp.computerCase.type')">Type</span>
          </dt>
          <dd>
            <span v-text="$t('configmeApp.CaseType.' + computerCase.type)">{{ computerCase.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.formats')">Formats</span>
          </dt>
          <dd>
            <span>{{ computerCase.formats }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.sizeMaxGpu')">Size Max Gpu</span>
          </dt>
          <dd>
            <span>{{ computerCase.sizeMaxGpu }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.sizeMaxVentirad')">Size Max Ventirad</span>
          </dt>
          <dd>
            <span>{{ computerCase.sizeMaxVentirad }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.sizeMaxPsu')">Size Max Psu</span>
          </dt>
          <dd>
            <span>{{ computerCase.sizeMaxPsu }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.hardDriveSlots')">Hard Drive Slots</span>
          </dt>
          <dd>
            <span>{{ computerCase.hardDriveSlots }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.frontPanelOutputs')">Front Panel Outputs</span>
          </dt>
          <dd>
            <span>{{ computerCase.frontPanelOutputs }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.fanIncluded')">Fan Included</span>
          </dt>
          <dd>
            <span>{{ computerCase.fanIncluded }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.fanSlotsAvailable')">Fan Slots Available</span>
          </dt>
          <dd>
            <span>{{ computerCase.fanSlotsAvailable }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.watercoolingCompatibility')">Watercooling Compatibility</span>
          </dt>
          <dd>
            <span>{{ computerCase.watercoolingCompatibility }}</span>
          </dd>
          <template v-if="computerCase.dimension">
            <dt>
              <span v-text="$t('configmeApp.dimension.height')">Height</span>
            </dt>
            <dd>
              <span>{{ computerCase.dimension.height }} mm</span>
            </dd>
            <dt>
              <span v-text="$t('configmeApp.dimension.width')">Width</span>
            </dt>
            <dd>
              <span>{{ computerCase.dimension.width }} mm</span>
            </dd>
            <dt>
              <span v-text="$t('configmeApp.dimension.length')">Length</span>
            </dt>
            <dd>
              <span>{{ computerCase.dimension.length }} mm</span>
            </dd>
          </template>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="computerCase.id"
          :to="{ name: 'ComputerCaseEdit', params: { computerCaseId: computerCase.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./computer-case-details.component.ts"></script>
