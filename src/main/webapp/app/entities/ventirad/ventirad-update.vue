<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="configmeApp.ventirad.home.createOrEditLabel"
          data-cy="VentiradCreateUpdateHeading"
          v-text="$t('configmeApp.ventirad.home.createOrEditLabel')"
        >
          Create or edit a Ventirad
        </h2>
        <div>
          <div class="form-group" v-if="ventirad.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="ventirad.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.ventirad.rangeFanSpeed')" for="ventirad-rangeFanSpeed"
              >Range Fan Speed</label
            >
            <input
              type="text"
              class="form-control"
              name="rangeFanSpeed"
              id="ventirad-rangeFanSpeed"
              data-cy="rangeFanSpeed"
              :class="{ valid: !$v.ventirad.rangeFanSpeed.$invalid, invalid: $v.ventirad.rangeFanSpeed.$invalid }"
              v-model="$v.ventirad.rangeFanSpeed.$model"
              required
            />
            <div v-if="$v.ventirad.rangeFanSpeed.$anyDirty && $v.ventirad.rangeFanSpeed.$invalid">
              <small class="form-text text-danger" v-if="!$v.ventirad.rangeFanSpeed.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.ventirad.noise')" for="ventirad-noise">Noise</label>
            <input
              type="number"
              class="form-control"
              name="noise"
              id="ventirad-noise"
              data-cy="noise"
              :class="{ valid: !$v.ventirad.noise.$invalid, invalid: $v.ventirad.noise.$invalid }"
              v-model.number="$v.ventirad.noise.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.ventirad.hasThermalPaste')" for="ventirad-hasThermalPaste"
              >Has Thermal Paste</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="hasThermalPaste"
              id="ventirad-hasThermalPaste"
              data-cy="hasThermalPaste"
              :class="{ valid: !$v.ventirad.hasThermalPaste.$invalid, invalid: $v.ventirad.hasThermalPaste.$invalid }"
              v-model="$v.ventirad.hasThermalPaste.$model"
              required
            />
            <div v-if="$v.ventirad.hasThermalPaste.$anyDirty && $v.ventirad.hasThermalPaste.$invalid">
              <small class="form-text text-danger" v-if="!$v.ventirad.hasThermalPaste.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.ventirad.dimension')" for="ventirad-dimension">Dimension</label>
            <select class="form-control" id="ventirad-dimension" data-cy="dimension" name="dimension" v-model="ventirad.dimension">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="ventirad.dimension && dimensionOption.id === ventirad.dimension.id ? ventirad.dimension : dimensionOption"
                v-for="dimensionOption in dimensions"
                :key="dimensionOption.id"
              >
                {{ dimensionOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.ventirad.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./ventirad-update.component.ts"></script>
