#version 150

#moj_import <lodestone:common_math.glsl>

uniform sampler2D Sampler0;
uniform sampler2D SceneDepthBuffer;
uniform sampler2D PaletteSampler;

uniform float LumiTransparency;
uniform float DepthFade;
uniform float PaletteRows;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform mat4 InvProjMat;
uniform vec2 ScreenSize;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in vec4 viewSpacePos;

out vec4 fragColor;

const vec3 PLACEHOLDER1 = vec3(1.0, 1.0, 1.0);
const vec3 PLACEHOLDER2 = vec3(183.0/255.0, 183.0/255.0, 183.0/255.0);
const vec3 PLACEHOLDER3 = vec3(135.0/255.0, 135.0/255.0, 135.0/255.0);
const vec3 PLACEHOLDER4 = vec3(102.0/255.0, 102.0/255.0, 102.0/255.0);
const vec3 PLACEHOLDER5 = vec3(79.0/255.0, 79.0/255.0, 79.0/255.0);
const vec3 PLACEHOLDER6 = vec3(61.0/255.0, 61.0/255.0, 61.0/255.0);

bool isPlaceholder(vec3 a, vec3 b) {
    const float epsilon = 0.05;
    return all(lessThan(abs(a - b), vec3(epsilon)));
}

void main() {
    vec4 texColor = texture(Sampler0, texCoord0);
    vec3 color = texColor.rgb;
    float alpha = texColor.a;

    int colorIndex = -1;
    if (isPlaceholder(color, PLACEHOLDER1)) colorIndex = 0;
    else if (isPlaceholder(color, PLACEHOLDER2)) colorIndex = 1;
    else if (isPlaceholder(color, PLACEHOLDER3)) colorIndex = 2;
    else if (isPlaceholder(color, PLACEHOLDER4)) colorIndex = 3;
    else if (isPlaceholder(color, PLACEHOLDER5)) colorIndex = 4;
    else if (isPlaceholder(color, PLACEHOLDER6)) colorIndex = 5;

    if (colorIndex != -1 && PaletteRows > 0.0) {
        float u = (colorIndex + 0.5) / 6.0;
        float v = vertexColor.r;
        vec4 paletteColor = texture(PaletteSampler, vec2(u, v));
        color = paletteColor.rgb;
    }

    vec4 finalColor = vec4(color, alpha) * vertexColor.a * ColorModulator;
    vec4 fogged = applyFog(finalColor, FogStart, FogEnd, FogColor, vertexDistance);
    if (fogged.a == 0.0) discard;

    float sceneDepthClip = getDepth(SceneDepthBuffer, gl_FragCoord.xy / ScreenSize);
    vec3 sceneViewSpace = viewSpaceFromDepth(sceneDepthClip, gl_FragCoord.xy / ScreenSize, InvProjMat);
    float depthFade = applyDepthFade(sceneViewSpace.z, viewSpacePos.z, DepthFade);
    fogged.a *= depthFade;
    fragColor = fogged;
}