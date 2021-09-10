<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="configmeApp.computerCase.home.createOrEditLabel"
          data-cy="ComputerCaseCreateUpdateHeading"
          v-text="$t('configmeApp.computerCase.home.createOrEditLabel')"
        >
          Create or edit a ComputerCase
        </h2>
        <div>
          <div class="form-group" v-if="computerCase.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="computerCase.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.computerCase.type')" for="computer-case-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.computerCase.type.$invalid, invalid: $v.computerCase.type.$invalid }"
              v-model="$v.computerCase.type.$model"
              id="computer-case-type"
              data-cy="type"
              required
            >
              <option value="PETITE" v-bind:label="$t('configmeApp.CaseType.PETITE')">PETITE</option>
              <option value="MOYENNE" v-bind:label="$t('configmeApp.CaseType.MOYENNE')">MOYENNE</option>
              <option value="GRANDE" v-bind:label="$t('configmeApp.CaseType.GRANDE')">GRANDE</option>
            </select>
            <div v-if="$v.computerCase.type.$anyDirty && $v.computerCase.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.computerCase.type.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.computerCase.formats')" for="computer-case-formats">Formats</label>
            <input
              type="text"
              class="form-control"
              name="formats"
              id="computer-case-formats"
              data-cy="formats"
              :class="{ valid: !$v.computerCase.formats.$invalid, invalid: $v.computerCase.formats.$invalid }"
              v-model="$v.computerCase.formats.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.computerCase.sizeMaxGpu')" for="computer-case-sizeMaxGpu"
              >Size Max Gpu</label
            >
            <input
              type="number"
              class="form-control"
              name="sizeMaxGpu"
              id="computer-case-sizeMaxGpu"
              data-cy="sizeMaxGpu"
              :class="{ valid: !$v.computerCase.sizeMaxGpu.$invalid, invalid: $v.computerCase.sizeMaxGpu.$invalid }"
              v-model.number="$v.computerCase.sizeMaxGpu.$model"
              required
            />
            <div v-if="$v.computerCase.sizeMaxGpu.$anyDirty && $v.computerCase.sizeMaxGpu.$invalid">
              <small class="form-text text-danger" v-if="!$v.computerCase.sizeMaxGpu.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.computerCase.sizeMaxGpu.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.computerCase.sizeMaxVentirad')" for="computer-case-sizeMaxVentirad"
              >Size Max Ventirad</label
            >
            <input
              type="number"
              class="form-control"
              name="sizeMaxVentirad"
              id="computer-case-sizeMaxVentirad"
              data-cy="sizeMaxVentirad"
              :class="{ valid: !$v.computerCase.sizeMaxVentirad.$invalid, invalid: $v.computerCase.sizeMaxVentirad.$invalid }"
              v-model.number="$v.computerCase.sizeMaxVentirad.$model"
              required
            />
            <div v-if="$v.computerCase.sizeMaxVentirad.$anyDirty && $v.computerCase.sizeMaxVentirad.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.computerCase.sizeMaxVentirad.required"
                v-text="$t('entity.validation.required')"
              >
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.computerCase.sizeMaxVentirad.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.computerCase.sizeMaxPsu')" for="computer-case-sizeMaxPsu"
              >Size Max Psu</label
            >
            <input
              type="number"
              class="form-control"
              name="sizeMaxPsu"
              id="computer-case-sizeMaxPsu"
              data-cy="sizeMaxPsu"
              :class="{ valid: !$v.computerCase.sizeMaxPsu.$invalid, invalid: $v.computerCase.sizeMaxPsu.$invalid }"
              v-model.number="$v.computerCase.sizeMaxPsu.$model"
              required
            />
            <div v-if="$v.computerCase.sizeMaxPsu.$anyDirty && $v.computerCase.sizeMaxPsu.$invalid">
              <small class="form-text text-danger" v-if="!$v.computerCase.sizeMaxPsu.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.computerCase.sizeMaxPsu.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.computerCase.hardDriveSlots')" for="computer-case-hardDriveSlots"
              >Hard Drive Slots</label
            >
            <input
              type="text"
              class="form-control"
              name="hardDriveSlots"
              id="computer-case-hardDriveSlots"
              data-cy="hardDriveSlots"
              :class="{ valid: !$v.computerCase.hardDriveSlots.$invalid, invalid: $v.computerCase.hardDriveSlots.$invalid }"
              v-model="$v.computerCase.hardDriveSlots.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('configmeApp.computerCase.frontPanelOutputs')"
              for="computer-case-frontPanelOutputs"
              >Front Panel Outputs</label
            >
            <input
              type="text"
              class="form-control"
              name="frontPanelOutputs"
              id="computer-case-frontPanelOutputs"
              data-cy="frontPanelOutputs"
              :class="{ valid: !$v.computerCase.frontPanelOutputs.$invalid, invalid: $v.computerCase.frontPanelOutputs.$invalid }"
              v-model="$v.computerCase.frontPanelOutputs.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.computerCase.fanIncluded')" for="computer-case-fanIncluded"
              >Fan Included</label
            >
            <input
              type="text"
              class="form-control"
              name="fanIncluded"
              id="computer-case-fanIncluded"
              data-cy="fanIncluded"
              :class="{ valid: !$v.computerCase.fanIncluded.$invalid, invalid: $v.computerCase.fanIncluded.$invalid }"
              v-model="$v.computerCase.fanIncluded.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('configmeApp.computerCase.fanSlotsAvailable')"
              for="computer-case-fanSlotsAvailable"
              >Fan Slots Available</label
            >
            <input
              type="text"
              class="form-control"
              name="fanSlotsAvailable"
              id="computer-case-fanSlotsAvailable"
              data-cy="fanSlotsAvailable"
              :class="{ valid: !$v.computerCase.fanSlotsAvailable.$invalid, invalid: $v.computerCase.fanSlotsAvailable.$invalid }"
              v-model="$v.computerCase.fanSlotsAvailable.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('configmeApp.computerCase.watercoolingCompatibility')"
              for="computer-case-watercoolingCompatibility"
              >Watercooling Compatibility</label
            >
            <input
              type="text"
              class="form-control"
              name="watercoolingCompatibility"
              id="computer-case-watercoolingCompatibility"
              data-cy="watercoolingCompatibility"
              :class="{
                valid: !$v.computerCase.watercoolingCompatibility.$invalid,
                invalid: $v.computerCase.watercoolingCompatibility.$invalid,
              }"
              v-model="$v.computerCase.watercoolingCompatibility.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.computerCase.dimension')" for="computer-case-dimension"
              >Dimension</label
            >
            <select class="form-control" id="computer-case-dimension" data-cy="dimension" name="dimension" v-model="computerCase.dimension">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  computerCase.dimension && dimensionOption.id === computerCase.dimension.id ? computerCase.dimension : dimensionOption
                "
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
            :disabled="$v.computerCase.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./computer-case-update.component.ts"></script>
